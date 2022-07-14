javac testercode.java
javac testercode2.java
java testercode2 > inp2
if !(diff -w -q inp2 out2)
    then
        echo "Error in Block Chain"
		diff -u inp2 out2
        # echo "======Expected====="
        # cat out
        # echo "======Your-Output======"
        # cat in1
        exit
    fi
echo "Block Chain Made correctly"
rm inp2
java testercode > inp1
if !(diff -w -q inp1 out1)
	then
		echo "Error in Proof of Score"
		diff -u inp1 out1
		exit
	fi
echo "Proof of Score is correct"
rm inp1

