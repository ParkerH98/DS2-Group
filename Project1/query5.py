import pandas as pd
import time
import matplotlib.pyplot as plt
import numpy as np
import random

def print_header(header):
   
    print(f'{header}')
#Merging based on different NFTs
def mergeSort(myList, col):
    if len(myList) > 1:
        mid = len(myList) // 2
        left = myList[:mid]
        right = myList[mid:]

        # Recursive call on each half
        mergeSort(left, col)
        mergeSort(right, col)

        # Two iterators for traversing the two halves
        i = 0
        j = 0

        # Iterator for the main list
        k = 0

        while i < len(left) and j < len(right):
            if left[i][col] > right[j][col]:
                # The value from the left half has been used
                myList[k] = left[i]
                # Move the iterator forward
                i += 1
            else:
                myList[k] = right[j]
                j += 1
            # Move to the next slot
            k += 1

        # For all the remaining values
        while i < len(left):
            myList[k] = left[i]
            i += 1
            k += 1

        while j < len(right):
            myList[k] = right[j]
            j += 1
            k += 1

# Sorting based on Transactions
def mergeSort_col2(myList, col):
    if len(myList) > 1:
        mid = len(myList) // 2
        left = myList[:mid]
        right = myList[mid:]

        # Recursive call on each half
        mergeSort_col2(left, col)
        mergeSort_col2(right, col)

        # Two iterators for traversing the two halves
        i = 0
        j = 0

        # Iterator for the main list
        k = 0

        while i < len(left) and j < len(right):
            if left[i][col] != right[j][col]:
                if left[i][col] > right[j][col]:
                    # The value from the left half has been used
                    myList[k] = left[i]
                    # Move the iterator forward
                    i += 1
                else:
                    myList[k] = right[j]
                    j += 1
                # Move to the next slot
                k += 1
            else:
                if left[i][col +1] > right[j][col +1]:
                    # The value from the left half has been used
                    myList[k] = left[i]
                    # Move the iterator forward
                    i += 1
                else:
                    myList[k] = right[j]
                    j += 1
                # Move to the next slot
                k += 1

        # For all the remaining values
        while i < len(left):
            myList[k] = left[i]
            i += 1
            k += 1

        while j < len(right):
            myList[k] = right[j]
            j += 1
            k += 1


def gather_runtimes(num_runs):
    runtime_start = time.time()
    runtimes = multi_run_query(num_runs)
    runtime_end = time.time()
    print("time to get all runtimes: " + str(runtime_end-runtime_start))
    print_header("Exporting the output into csv file")
    
    runtimes = pd.DataFrame(runtimes, columns=['Avg Runtime'])
    runtimes.to_csv('avg_runtimes.csv', index = False)
    # using generated csv in excel to make a plot
    
def multi_run_query(num_runs):
    runtimes = []
    for input_size in range(1000, max_len + 1000, 1000):
        average_runtime = 0
        
        for i in range(num_runs):
            start = time.time()
            mergeSort(final_buyer_list[:input_size], 1)
            mergeSort_col2(final_buyer_list[:input_size], 1)
            end = time.time()
            average_runtime += (end-start)
            # average_runtime = average_runtime * 10**9
            random.shuffle(final_buyer_list)
        average_runtime /= num_runs
        print("Input: " + str(len(final_buyer_list[:input_size])) + " Avg Time (secs): " + str(average_runtime))
        
        runtimes.append(average_runtime)
    return runtimes

if __name__ == '__main__':
    print_header('Reading the data')
    nft_data = pd.read_csv("datasets/nft_dataset.csv")
    nft_data.info()
    
    print_header('Removing null values from data')
    print(nft_data.isna().sum())
    nft_data.dropna(inplace=True)
    
    print_header('Grouping data based on buyers')
    grp_nft = nft_data.groupby('Buyer').agg({'NFT': lambda x: x.nunique(), 'Txn Hash': ['count']}).reset_index()
    grp_nft.columns = [' '.join(str(i) for i in col) for col in grp_nft.columns]
    grp_nft.reset_index(inplace=True)
    grp_nft = grp_nft.drop('index', axis=1)
    grp_nft.rename(
        columns={'Buyer ': 'Buyer', 'Txn Hash count': 'No of Transactions', 'NFT <lambda>': 'Different NFTs'},
        inplace=True)
    print(grp_nft.head())
    
    print_header("Converting dataframe to list")
    final_buyer_list = grp_nft.values.tolist()
    final_buyer_list
    
    print_header("Starting the sorting")
    start = time.time()
    mergeSort(final_buyer_list, 1)
    mergeSort_col2(final_buyer_list, 1)
    end = time.time()
    time_taken_string = "The time of execution of above program is :" + str((end - start)) + " secs"
    print_header(time_taken_string)
    print_header("Changing the list to dataframe")
    sorted_buyer_data = pd.DataFrame(final_buyer_list,
                                     columns=['Buyer', 'Different NFTs', 'No of Transactions'])
    print(sorted_buyer_data.info)
    print_header("Exporting the output into csv file")
    sorted_buyer_data.to_csv('sorted_buyer_dataset.csv')
    
    max_len = round(len(final_buyer_list) / 1000) * 1000
    input_intervals = np.arange(1000, max_len + 1000, 1000)

    # runs the dataset with various sizes & outputs the results in a csv
    # gather_runtimes(100)
    
    # plt.plot(input_intervals, runtimes)
    # plt.xlabel("Input Size")
    # plt.ylabel("Runtime (ns)")
    # plt.show()
    