mjavac:		y.tab.c lex.yy.o node.cc generator.cc
			clang++ -Wno-pragma-once-outside-header y.tab.c lex.yy.o generator.cc -g -o mjavac -lfl

y.tab.c:	parser.y
			bison -v -y -d -g -t --verbose parser.y

lex.yy.c:	parser.l
			lex -l parser.l

lex.yy.o:   lex.yy.c
			clang++ -c lex.yy.c

lexParser: 	lex.yy.o
			gcc lex.yy.o -o lexParser

clean:
	rm -f lex.yy.c y.tab.c y.tab.h y.dot y.output mjavac lex.yy.o *.s
