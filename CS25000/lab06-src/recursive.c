#include <stdio.h>

int main(void) {
	printf("%d\n", multiply(11, 13));
}

int multiply(int a, int n) {
	if (n > 2) {
		return a + multiply(a, n - 1);
	}
	return a + a;
}
