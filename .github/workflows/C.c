#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>

int main() {

    /* Enter your code here. Read input from STDIN. Print output to STDOUT */ 
    int n = 5;
    int c = 1;
    int b = 2;

    int arr[] = {1,2,3,4,5};
    
  
    
    int max = arr[0];
    
    for(int j=0; j<n; j++){
        if(arr[j] > max)
            max = arr[j];
    }
    
    if(max/b == c)
        printf("%s", "Yes");
    else
        printf("%s", "No");
    
    return 0;
}
