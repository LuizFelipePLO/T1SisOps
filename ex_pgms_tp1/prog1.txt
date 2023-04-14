.code
  load a
  add #1
  store a
  syscall 0
.endcode

.data
  a 10
.enddata

Acc <- 10
Acc <- 10+1 = 11
PMEM[a] <- 11
exit