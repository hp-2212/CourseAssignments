g++ -std=c++17 tc.cpp -o tc
g++ -std=c++17 tc2.cpp -o tc2
javac kdtree.java
javac sample.java
touch out2.txt
((n=20))
((score=0))
((marks=5000))
((total=0))
for ((t=1;t<=n;t++))
do
    ./tc>queries.txt
    ./tc2>restaurants.txt
    java kdtree
    java sample
    ((total=total+marks))
    if  !(diff -w -q output.txt out2.txt)
    then 
        echo "TestCase (${t}/${n}): failed"
        ((score=score+0))
	else 
		echo "TestCase (${t}/${n}): passed"
        ((score=score+marks))
    fi
done
echo ""
echo "Score:${score}/${total}"
rm *.class
rm tc
rm tc2
rm out2.txt
