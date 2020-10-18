all: prog2 prog2_rdt prog2_sr

prog2: prog2.c prog2.h
	gcc -Wall -Wextra -g prog2.c -o prog2

prog2_rdt: prog2_rdt.c prog2.h
	gcc -Wall -Wextra -g prog2_rdt.c -o prog2_rdt

prog2_sr: prog2_sr.c prog2.h
	gcc -Wall -Wextra -g prog2_sr.c -o prog2_sr

clean:
	rm -f prog2 prog2_rdt *~ *.swp
