#include <iostream>
#include <stdlib.h>
#include "app.h"

int main () {
    lab::Greeter greeter;
    std::cout << greeter.greeting() << std::endl;
    return 0;
}
