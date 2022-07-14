#include <bits/stdc++.h>
#include <random>

using namespace std;
#define uid(a,b) uniform_int_distribution<long long>(a,b)(rng)
mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());

int main()
{
    cout<<"latitude,longitude"<<endl;
    int n=1000;
    for(int i=0;i<n;i++)cout<<uid(-1000,1000)<<","<<uid(-1000,1000)<<endl;
}
