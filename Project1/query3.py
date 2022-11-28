import pandas as pd
import itertools 
import csv
import time
import math
from math import nan
  
def mergeSort(array):
    if len(array) > 1:

        #  r is where the parent array is divided into two subarrays
        r = len(array)//2
        Left = array[:r]
        Right = array[r:]

        # Sort the two halves
        mergeSort(Left)
        mergeSort(Right)

        i = j = k = 0

        while i < len(Left) and j < len(Right):
            if Left[i][1] < Right[j][1]:
                array[k] = Left[i]
                i += 1
            else:
                array[k] = Right[j]
                j += 1
            k += 1

        while i < len(Left):
            array[k] = Left[i]
            i += 1
            k += 1

        while j < len(Right):
            array[k] = Right[j]
            j += 1
            k += 1
 
# Print the array
def printList(array):
    array.reverse() # Descending order
    newList = [t for t in array if not any(isinstance(n, float) and math.isnan(n) for n in t)]
    dict = {'Buyer, Frequency': newList} 
    df = pd.DataFrame(dict)
    df.to_csv('Buyer1.csv')
    
    
#main function
if __name__ == '__main__':
    df = pd.read_csv("./datasets/nft_dataset.csv")

    #converting the column to list
    outputset = df.Buyer.values.tolist()
    
    # newset = df.values.tolist()

    #getting the frequency for the Buyer
    freq = {}
    for i in outputset:
        if i in freq:
            freq[i] += 1
            
        else:
            freq[i] = 1

    #convertung the dict to list for sorting
    array = list(freq.items())
    print(type(df))
    start = time.time()
    print("Sorting Started")
    mergeSort(array)
    end = time.time()
    print("Sorting Completed in", end - start, "Seconds")

    #Printing the data
    printList(array)

    #writing the data to CSV
    start = time.time()
    print("Writing to File Operation Started")
    with pd.option_context('mode.chained_assignment', None):
        with open('Buyer2.csv', mode = 'w', newline='') as csv_file:  
            csv_writer = csv.writer(csv_file)
            df['Frequency'] = pd.Series(dtype='int')
            csv_writer.writerow(df.head())
            for i in range(len(array)):
                ot = df.loc[df["Buyer"] == array[i][0]]
                ot["Frequency"] = array[i][1]
                for j in ot.values.tolist():
                    csv_writer.writerows([j])
    end = time.time()
    print("Writing Completed in"," ", end - start, "Seconds")
    