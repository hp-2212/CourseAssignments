from random import seed
from random import randint
#Set of coordinates
x_coordinates = set()
y_coordinates = set()

#Create txt files
f1 = open("restaurants.txt", "w")
f2 = open("queries.txt", "w")
f3 = open("output_res.txt", "w")
f1.write("latitude,longitude\n")
f2.write("latitude,longitude\n")

#Generate Restaurants
restaurants = set()
for _ in range(500):
	x = randint(0, 1000)
	while x in x_coordinates:
		x = randint(0,1000)
	y = randint(0, 1000)
	while y in y_coordinates:
		y = randint(0,1000)
	x_coordinates.add(x)
	y_coordinates.add(y)
	restaurants.add((x,y))
	f1.write(str(x)+","+str(y)+"\n")

#Queries
queries = set()
for _ in range(40):
	x = randint(0,1000)
	y = randint(0,1000)
	while (x,y) in queries:
		x = randint(0,1000)
		y = randint(0,1000)
	queries.add((x,y))

#Number of restaurants per query
for query in queries:
	x_range = (query[0]-100,query[0]+100)
	y_range = (query[1]-100,query[1]+100)
	f2.write(str(query[0])+","+str(query[1])+"\n")
	num_restaurants = 0
	for r in restaurants:
		if r[0] <= x_range[1] and r[0] >= x_range[0] and r[1] <= y_range[1] and r[1] >= y_range[0]:
			num_restaurants += 1
	f3.write(str(num_restaurants)+"\n")

#Closing files
f1.close()
f2.close()
f3.close()
