from pyparsing.helpers import delimited_list
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

# Creating our merge sort functions

def mergeSort(listOfItems, sortingField):
     if len(listOfItems) > 1:
 
         mid = len(listOfItems)//2
         left_list = listOfItems[:mid]
         right_list = listOfItems[mid:]
 
         mergeSort(left_list, sortingField)
         mergeSort(right_list, sortingField)
 
         i = 0
         j = 0
         k = 0
 
         while i < len(left_list) and j < len(right_list):
             if left_list[i][sortingField] < right_list[j][sortingField]:
                 listOfItems[k] = left_list[i]
                 i += 1
             else:
                 listOfItems[k] = right_list[j]
                 j += 1
             k += 1
 
         while i < len(left_list):
             listOfItems[k] = left_list[i]
             i += 1
             k += 1
 
         while j < len(right_list):
             listOfItems[k] = right_list[j]
             j += 1
             k += 1

# increasing the default pythons recursion limit for large inputs
sys.setrecursionlimit(10**6)

# runTimes = []
# n=1000
# i=0

# def calculateAvgerageTime(arrayToSort):
#     totalTimes = []
#     for i in range(0,100):
#         # record the start time before sorting
#         start_time = time.time()
#         # Quick Sorting
#         mergeSort(arrayToSort, 'Number of Buyers')
#         # record end time
#         end_time = time.time()
#         # Check time taken
#         total_time = end_time-start_time
#         totalTimes.append(total_time)
#     avgTime = sum(totalTimes)/100
#     return avgTime

# for i in range(0, len(listOfDictionaries)):
#     if i == n or i == len(listOfDictionaries)-1:
#         copyOfRecords = []
#         for j in range(0, i):
#             copyOfRecords.append(listOfDictionaries[j])
#         timeTaken = calculateAvgerageTime(copyOfRecords)
#         # push to run times list seconds*1000000000 nanoseconds
#         runTimes.append(timeTaken)
#         #deleting our array of records
#         del copyOfRecords
#         n += 1000

# pltTime = np.array(runTimes)
# maxLength = round(len(listOfDictionaries) / 1000) * 1000
# input_intervals = np.arange(1000, maxLength + 1000, 1000)
# plt.plot(input_intervals, pltTime, marker = "o", color = "b")
# plt.xlabel("Input Size")
# plt.ylabel("Runtime (ns)")
# plt.show()

mergeSort(listOfDictionaries, 'Number of Buyers')
headers = ['Token ID', 'Number of Buyers', 'Txn Hash', 'UnixTimestamp', 'Date Time (UTC)', 'Action', 'Buyer', 'NFT', 'Type', 'Quantity', 'Price', 'Market']
with open('sorted_dataset.csv', 'w') as csvfile:
  wtr = csv.DictWriter(csvfile, fieldnames=headers)
  wtr.writeheader()
  for x in listOfDictionaries:
    wtr.writerow(x)
