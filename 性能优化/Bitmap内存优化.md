# TravelInAndroid. 

# 三。性能优化：2021-09-01～2021-09-30.
## 09-04 Bitmap内存优化
### 1.Bitmap内存模型
    1.API10之前Bitmap自身在Dalvik Heap中，像素在Native中
    2.API10之后像素也被放在了Dalvik Heap中
    3.API26之后，像素在Native中
### 2.获取Bitmap占用内存
    1.getByteCount
    2.宽*高*1像素占用的内存*压缩比例