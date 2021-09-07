# TravelInAndroid.
# 二。架构知识：2021-08-01～2021-08-31.
#### 4.1 ButterKnife-->07-29
    ButterKnife是依赖注入框架，帮助我们实现findViewById/setOnClickListener等常规操作
    1。implementation 'com.jakewharton:butterknife:10.2.3'
       annotationProcessor 'com.jakewharton:butterknife-compiler:10.2.3'
    当前版本的原理是：APT+反射
    其中，APT=Annotation Processor Tool 专门用来处理注解，该注解作用域编译期，并将有注解的类，生成XXX_ViewBinding.class并跟随代码打包到apk中
    反射作用在运行阶段，ButterKnife.bind(this)时,会拿当前类的Class并拼接上_ViewBinding，然后使用ClassLoader将第一步中随代码打包进入apk的XXX_ViewBinding.class加载进来，
    并拿到该Class的构造方法，然后通过constructor.newInstance(target, source);方法调用到XXX_ViewBinding.class的构造方法，并在构造方法中将目标Activity/Fragment/CustomView等类中
    有注解的View或者OnClick设置点击事件
    问题：
    1。注解如何处理，会扫描所有的类吗？是运行时注解or编译时注解？
    A：运行时注解
    @Retention(RUNTIME) @Target(FIELD)
    public @interface BindView {
      /** View ID to which the field will be bound. */
      @IdRes int value();
    }

    /butterknife-compiler/src/main/java/butterknife/compiler/ButterKnifeProcessor.java

      private Set<Class<? extends Annotation>> getSupportedAnnotations() {
      //支持的注解类型
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

        annotations.add(BindAnim.class);
        annotations.add(BindArray.class);
        annotations.add(BindBitmap.class);
        annotations.add(BindBool.class);
        annotations.add(BindColor.class);
        annotations.add(BindDimen.class);
        annotations.add(BindDrawable.class);
        annotations.add(BindFloat.class);
        annotations.add(BindFont.class);
        annotations.add(BindInt.class);
        annotations.add(BindString.class);
        annotations.add(BindView.class);
        annotations.add(BindViews.class);
        annotations.addAll(LISTENERS);

        return annotations;
      }
    2。注解新生成的模板类如何与当前Activity/Fragment/CustomView中对应的view对应起来
    A:注解生成XXX_ViewBinding.class时，其构造方法会传入当前类对象，这样就拿到了当前类的对象，然后再对其成员变量赋值or完成方法调用
    @UiThread
      public EventBusMainActivity_ViewBinding(final EventBusMainActivity target, View source) {
      //当前activity的对象
        this.target = target;

        View view;
        view = Utils.findRequiredView(source, R.id.test_post, "field 'testPostTv' and method 'testPost'");
        //赋值
        target.testPostTv = Utils.castView(view, R.id.test_post, "field 'testPostTv'", TextView.class);
        view7f0900eb = view;
        //设置点击事件
        view.setOnClickListener(new DebouncingOnClickListener() {
          @Override
          public void doClick(View p0) {
            target.testPost();
          }
        });
      }

