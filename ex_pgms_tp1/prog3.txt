.code
  syscall 2		# digitar numero 2: acc<- 2
  store numero		# pmem[numero] <- 2
loop:
  sub controle		# acc <- 2-2=0
  BRZERO fim		# salta
  load numero
  div controle
  add b
  store aux
  load b
  store a
  load aux
  store b
  load controle
  sub #1
  store controle
  BRANY loop
fim:
  load b		# acc <- 3
  syscall 1		# print 3
  syscall 0		# exit
.endcode

.data
  numero 0
  controle 2
  aux 0
  b 3
  a 0
.enddata