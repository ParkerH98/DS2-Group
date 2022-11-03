# CS 5413 Assignment
# Algorithms for NFT Transaction Data Analytics
# Query 4: Sort down “Token ID’s” by the number of different buyers (“Buyer”).
import math
import copy
import sys
import time
import csv
import matplotlib.pyplot as plt
import numpy as np

# Getting the data from file using csv module
with open("./nft_dataset_small.csv") as fp:
    reader = csv.reader(fp, delimiter=",", quotechar='"')
    data = [row for row in reader]

# Converting the data into dictonary for counting number of buyers
#Ex: {'tokenID': noOfBuyers}
dictonary = {}

# looping through all the data once skipping first record(header)
for i in range(1, len(data)):
    # Extract token ID
    tokenID = data[i][6]
    buyer = data[i][4]
    # If token id exists increase buyer count by 1
    # Else add a new dictonary entry
    if tokenID in dictonary:
        dictonary[tokenID] += 1
    else:
        dictonary[tokenID] = 1

# Adding our new found num of buyer values to original data in list format so we can quick sort on it
# Example [{'Token ID': 648, 'Number of Buyers': 3}, {'Token ID': 123, 'Number of Buyers': 6}]
listOfDictionaries = []

for i in range(1, len(data)):
    tokenID = data[i][6]
    temp = {'Token ID': tokenID, 'Number of Buyers': dictonary[tokenID]}
    listOfDictionaries.append(temp)

# storing totalNumberOfRecords to prevent re calculation
totalRecords = len(listOfDictionaries)

# Creating our quick sort functions

# This is the naive approach which uses extra memory by creating new arrays in every recursive call
# But using the extra memory we are guaranteed quick sorts best time of nlogn
# Clocked at roughly 20s on a macbook pro for 92000 records


def quickSortNSpace(list, sortingField):
    # we return when the list length is 1 or less
    if len(list) <= 1:
        return list
    else:
        # choosing last element as the pivot for our quick sort
        pivot = list.pop()
        leftList = []
        rightList = []

        # if item is leff than or eual add to the left list
        # else add to the right list
        for item in list:
            if item[sortingField] <= pivot[sortingField]:
                rightList.append(item)
            else:
                leftList.append(item)
        # recursively calling function for smaller lists.
        return quickSortNSpace(leftList, sortingField) + [pivot] + quickSortNSpace(rightList, sortingField)


# This is less space edition of quick sort.
# It doesn't create any new arrays instead swaps items around in the same list
# Clocked around 34s on macbook pro for 92000 records

def quickSortLogNSpace(list, left, right, sortingField):
    # this condition will stop recursive call after all swaps
    if left < right:
        # choosing the last element of our search window as the pivot
        pivot = list[right]

        # i is tracking the point in the list where all items are less than pivot
        i = left - 1
        # j starts one index ahead of i and scans the items
        for j in range(left, right):
            # if any items is less than the pivot we swap items at i and j and increment i
            if list[j][sortingField] <= pivot[sortingField]:
                i += 1
                list[i], list[j] = list[j], list[i]
        # we finally swap the pivot into corrects place right ahead of i
        list[i + 1], list[right] = list[right], list[i + 1]

        # recursively calling function for smaller lists.
        quickSortLogNSpace(list, left, i, sortingField)
        quickSortLogNSpace(list, i+2, right, sortingField)


# increasing the default pythons recursion limit for large inputs
sys.setrecursionlimit(10**6)

copy1ofRecords = copy.deepcopy(listOfDictionaries)
copy2ofRecords = copy.deepcopy(listOfDictionaries)
copy3ofRecords = copy.deepcopy(listOfDictionaries)

print(len(listOfDictionaries))
start_time = time.time()
# Quick Sorting
quickSortNSpace(copy1ofRecords, 'Number of Buyers')
# record end time
end_time = time.time()
# Check time taken
total_time = end_time-start_time
print(total_time)

print(len(listOfDictionaries))
start_time = time.time()
# Quick Sorting
quickSortLogNSpace(copy2ofRecords, 0, totalRecords-1, 'Number of Buyers')
# record end time
end_time = time.time()
# Check time taken
total_time = end_time-start_time
print(total_time)


# using a list to record 100 run time numbers
runTimes = []
asymptomaticTime = []
recordCount = []
count = 0
for i in range(0, 99):
    copyOfRecords = copy.deepcopy(copy3ofRecords)
    count += 1
    # record the start time before sorting
    start_time = time.time()
    # Quick Sorting
    quickSortLogNSpace(copyOfRecords, 0, totalRecords-1, 'Number of Buyers')
    # record end time
    end_time = time.time()
    # Check time taken
    total_time = end_time-start_time
    # push to run times list seconds*1000000000 nanoseconds
    runTimes.append(total_time*1000000000)
    # push to asymptomatic time n * log(n) base 2
    asymptomaticTime.append(totalRecords*math.log(totalRecords, 2))
    # increasing record count
    recordCount.append(count)
    del copyOfRecords

# calculating average
averageTime = sum(runTimes)/len(runTimes)

# plotting a graph with calculated times
#plt.plot(recordCount, asymptomaticTime, label='Asymptomatic Times')
plt.plot(recordCount, runTimes, label='Actual Run Times')
plt.legend()
plt.show()