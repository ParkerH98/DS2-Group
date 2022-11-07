# CS 5413 Assignment
# Algorithms for NFT Transaction Data Analytics
# Query 4: Sort down “Token ID’s” by the number of different buyers (“Buyer”).

import pandas as pd
import math
import copy
import sys
import time
import csv
import matplotlib.pyplot as plt
import numpy as np

# Getting the data from file using csv module
with open("./datasets/nft_dataset.csv") as fp:
    reader = csv.reader(fp, delimiter=",", quotechar='"')
    data = [row for row in reader]


# Converting the data into dictonary for counting number of buyers
#Ex: {'tokenID': noOfBuyers}
dictonary = {}
buyer_dic = {}

# looping through all the data once skipping first record(header)
for i in range(1, len(data)):
    # Extract token ID and buyer
    buyer = data[i][4]
    tokenID = data[i][6]
    
    # If token id exists and it is a new buyer then increase token ID's buyer count by 1
    # If token ID is not new then add the new buyer to the token IDs buyer array
    if tokenID in dictonary:
        if not buyer in buyer_dic[tokenID]:
            buyer_dic[tokenID].append(buyer)
            dictonary[tokenID] += 1

    # Else if token ID is new add a new dictonary entry for the token ID 
    else:
        buyer_array = []
        buyer_array.append(buyer)
        dictonary[tokenID] = 1
        buyer_dic[tokenID] = buyer_array

# Adding our new found num of buyer values to original data in list format so we can quick sort on it
# Example [{'Token ID': 648, 'Number of Buyers': 3}, {'Token ID': 123, 'Number of Buyers': 6}]
listOfDictionaries = []

for i in range(1, len(data)):
    tokenID = data[i][6]
    txnHash = data[i][0]
    unixTimestamp = data[i][1]
    dateTime = data[i][2]
    action = data[i][3]
    buyer = data[i][4]
    nft = data[i][5]
    nType = data[i][7]
    quantity = data[i][8]
    price = data[i][9]
    market = data[i][10]
    
    temp = {'Token ID': tokenID, 'Number of Buyers': dictonary[tokenID], 'Txn Hash': txnHash , 'UnixTimestamp': unixTimestamp, 'Date Time (UTC)': dateTime, 'Action': action, 'Buyer': buyer, 'NFT': nft, 'Type': nType, 'Quantity': quantity, 'Price': price, 'Market': market}
    listOfDictionaries.append(temp)

# storing totalNumberOfRecords to prevent re calculation
totalRecords = len(listOfDictionaries)

# Creating our quick sort functions

# This is the naive approach which uses extra memory by creating new arrays in every recursive call
# But using the extra memory we are guaranteed quick sorts best time of nlogn

def quickSortNSpace(list, sortingField):
    # we return when the list length is 1 or less
    if len(list) <= 1:
        return list
    else:
        # choosing last element as the pivot for our quick sort
        pivot = list.pop()
        leftList = []
        rightList = []

        # if item is less than or equal, add to the left list
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

runTimes = []
n=1000
i=0

def calculateAvgerageTime(arrayToSort):
    totalTimes = []
    for i in range(0,100):
        # record the start time before sorting
        start_time = time.time()
        # Quick Sorting
        quickSortLogNSpace(arrayToSort, 0, len(arrayToSort)-1, 'Number of Buyers')
        # record end time
        end_time = time.time()
        # Check time taken
        total_time = end_time-start_time
        totalTimes.append(total_time)
    avgTime = sum(totalTimes)/100
    return avgTime

for i in range(0, len(listOfDictionaries)):
    if i == n or i == len(listOfDictionaries)-1:
        copyOfRecords = []
        for j in range(0, i):
            copyOfRecords.append(listOfDictionaries[j])
        timeTaken = calculateAvgerageTime(copyOfRecords)
        # push to run times list seconds*1000000000 nanoseconds
        runTimes.append(timeTaken)
        #deleting our array of records
        del copyOfRecords
        n += 1000

print(runTimes)

quickSortLogNSpace(listOfDictionaries, 0, totalRecords-1, 'Number of Buyers')
wtr = csv.writer(open ('query4_sorted.csv', 'w'), delimiter=',', lineterminator='\n')
for x in listOfDictionaries : wtr.writerow ([x])

pltTime = np.array(runTimes)
max_len = round(len(listOfDictionaries) / 1000) * 1000
print(max_len)
input_intervals = np.arange(1000, max_len + 1000, 1000)
print(input_intervals)
plt.plot(input_intervals, pltTime, marker = "o", color = "b")
plt.xlabel("Input Size")
plt.ylabel("Runtime (ns)")
plt.show()
