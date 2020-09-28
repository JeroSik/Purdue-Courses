#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>

void old_deprecated_func(void)
{
        printf("This is an old function no longer used\n");
        printf("Perhaps the developers should delete it...?\n");
        printf("Or maybe it serves some secret purpose to the assignment...\n");
}

void coupon(char * arg)
{
        char name[16];
        char * stuff[4] = {"iPad 2", "HTC Thuderbolt", "iPhone 4", "Motorola Xoom"};

        strcpy(name, arg);
        printf("Our loyal customer %s:\n", name);
        printf("Use this coupon to redeem your free %s!\n", stuff[rand()%4]);
        printf("Coupon number %d\n\n", rand());
        printf("name: %p\n", &name);
}

int main(int argc, char ** argv)
{
        if (argc < 2) {
                printf("usage: %s your_name\n", argv[0]);
                return 0;
        }

        srand(time(0));
        coupon(argv[1]);

        return 0;
}

