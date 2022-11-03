import pandas as pd
import itertools 
import csv
import time
  
  
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
    dict = {'Buyer, Frequency': array} 
    df = pd.DataFrame(dict)
    df.to_csv('Buyer1.csv')
    
    
    
if __name__ == '__main__':
    df = pd.read_csv("./datasets/nft_dataset.csv")

    #converting the column to list
    outputset = df.Buyer.values.tolist()
    
    
    
    newset = df.values.tolist()

    #getting the frequency for the Buyer
    freq = {}
    for i in outputset:
        if i in freq:
            freq[i] += 1
            
        else:
            freq[i] = 1
    
    # out = dict(itertools.islice(freq.items(), 100)) 

    # print("{" + "\n".join("{!r}: {!r},".format(k, v) for k, v in out.items()) + "}")

    
    # print(freq)
    newarray = []
    array = list(freq.items())
    print(type(df))
    start = time.time()
    print("hello")
    mergeSort(array)
    end = time.time()
    print(end - start, "Seconds")
    printList(array)
    # csvdataset = df.values.tolist()
    print(len(array))
    newlist = []
    with open('Buyer2.csv', mode = 'w', newline='') as csv_file:  
        csv_writer = csv.writer(csv_file)
        df['Frequency'] = pd.Series(dtype='int')
        csv_writer.writerow(df.head())
        for i in range(len(array)):
            # for j in range(array[i][1]):
            # print(df.loc[df["Buyer"] == array[i][0]])
            ot = df.loc[df["Buyer"] == array[i][0]]
            # ot["new"] = array[i][1]
            # print(ot)
            ot["Frequency"] = array[i][1]
            # newlist.append(ot.values.tolist())
            # print(newlist)
            # print(ot)
            for j in ot.values.tolist():
                csv_writer.writerows([j])
            # newlist.append(ot.values.tolist())
            # print(len(newlist))
        # print(len(newlist))
            # ot.to_csv('Buyer2.csv')
    # print(len(newlist[0]))
                
            
    # array.insert(0,(array[0][0], array[0][1], "hell0"))
    # print(array[0])
    # print(type(array), type(array[0]), type(array[0][0]))
    # print(df['Buyer'].value_counts().reset_index())
    