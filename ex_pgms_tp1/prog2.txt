.code
  load controle		# acc <- 0
  syscall 2		# digitar numer 1: acc<- 1
  store controle	# pmem[controle] <- 1
loop:
  BRZERO fim		# 2a. Volta sai
  load a		# acc <- 0
  add b			# acc<- 0+1 = 1
  store aux		# pmem[aux] <- 1
  load b		# acc <- 1
  store a		# pmem[a] <- 1
  load aux		# acc <- 1
  store b		# pmem[b] <- 1
  load controle		# acc <- 1
  sub #1		# acc<- 1-1=0
  store controle	# pmem[controle] <- 0
  BRANY loop
fim:
  load b		# acc <- 1
  syscall 1		# print 1
  syscall 0
.endcode

.data
  a 0
  b 1
  controle 0
  aux 0
.enddata