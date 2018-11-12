#include <stdio.h>
#include "insertionSort.h"

int main() {
	int array[10] = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0};
	insertionSort(array, 10);
	for (int i = 0; i < 10; i++) {
		printf("%d\n", array[i]);
	}
}
