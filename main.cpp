#include <iostream>
#include "View.h"
#include "DemoApp.h"

int main (int argc, char * const argv[]) {
	View::init(800, 600, false);
	View::start(new DemoApp());
    return 0;
}
//
//void * hello(void * id){
//	int s = *(int *)id;
//	while(true){
//		printf("Hello! %d\n", s);
//		sleep(s);
//	}
//}
//
//int a[] = {
//	1,2,3,4,5
//};
//
//int main(int argc, char * const argv[]){
//	pthread_t threads[5];
//	
//	pthread_create(&threads[0], NULL, hello, &a[0]);
//	pthread_create(&threads[1], NULL, hello, &a[1]);
//	pthread_create(&threads[2], NULL, hello, &a[2]);
//	pthread_create(&threads[3], NULL, hello, &a[3]);
//	
//	pthread_exit(NULL);
//	
//	return 0;
//}