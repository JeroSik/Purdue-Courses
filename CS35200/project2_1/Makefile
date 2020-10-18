interpret:		y.tab.c lex.yy.o node.cc typecheck.cc interpret.cc
			clang++ -Wno-pragma-once-outside-header y.tab.c lex.yy.o typecheck.cc  interpret.cc node.cc -g -o interpret -lfl

y.tab.c:	parser.y
			bison -v -y -d -g --verbose parser.y

lex.yy.c:	parser.l
			lex -l parser.l

lex.yy.o:   lex.yy.c
			clang++ -c lex.yy.c

lexParser: 	lex.yy.o
			gcc lex.yy.o -o lexParser
clean:
	rm -f lex.yy.c lex.yy.o parser.dot parser.output parser.tab.c parser.tab.h y.dot y.output y.tab.c y.tab.h
# clean:
# 	rm -f lex.yy.c y.tab.c y.tab.h y.dot y.output interpret lex.yy.o