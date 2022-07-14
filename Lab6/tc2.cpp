#include <bits/stdc++.h>
#include <random>

using namespace std;
#define uid(a,b) uniform_int_distribution<long long>(a,b)(rng)
mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());

int main()
{
    cout<<"latitude,longitude"<<endl;
    int n=1000;
	set<int> xx,yy;
	for(int i=0;i<n;i++){
		xx.insert(uid(-1000,1000));
		yy.insert(uid(-1000,1000));
	}
	vector<int> x;
	vector<int> y;
	while(xx.size()>0){x.push_back(*xx.begin());xx.erase(xx.begin());}
	while(yy.size()>0){y.push_back(*yy.begin());yy.erase(yy.begin());}
	shuffle(x.begin(),x.end(),rng);
	shuffle(y.begin(),y.end(),rng);
    for(int i=0;i<min(x.size(),y.size());i++)cout<<x[i]<<","<<y[i]<<endl;
}