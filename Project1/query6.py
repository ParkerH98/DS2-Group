import pandas as pd
import time
import matplotlib.pyplot as plt
import numpy as np
import random

INTERVAL = 4

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

def gather_runtimes(num_runs):
    runtime_start = time.time()
    runtimes = multi_run_query(num_runs)
    runtime_end = time.time()
    print("time to get all runtimes: " + str(runtime_end-runtime_start))
    print_header("Exporting the output into csv file")
    
    runtimes = pd.DataFrame(runtimes, columns=['Avg Runtime', 'Input Size'])
    runtimes.to_csv('query6_avg_runtimes.csv', index = False, columns=['Avg Runtime', 'Input Size'])
    # using generated csv in excel to make a plot
    
def multi_run_query(num_runs):
    max_len = (len(final_fraud_list) / INTERVAL) * INTERVAL
    max_len = int(max_len)
    runtimes = []
    for input_size in range(INTERVAL, max_len + INTERVAL, INTERVAL):
        average_runtime = 0
        
        for i in range(num_runs):
            start = time.time()
            mergeSort(final_fraud_list[:input_size], 4)
            end = time.time()
            average_runtime += (end-start)
            random.shuffle(final_fraud_list)
        average_runtime /= num_runs
        print("Input: " + str(len(final_fraud_list[:input_size])) + " Avg Time (secs): " + str(average_runtime))
        
        runtimes.append([average_runtime, input_size])
    return runtimes

if __name__ == '__main__':
    print_header('Reading the data')
    nft_data = pd.read_csv("datasets/nft_dataset.csv")
    nft_data.info()
    
    print_header('Removing null values from data')
    print(nft_data.isna().sum())
    nft_data.dropna(inplace=True)
    
    print_header('Grouping data based on NFTs')
    grp_nft = nft_data.groupby(['NFT']).agg({'Buyer': lambda x: x.nunique(), 'Txn Hash': ['count']}).reset_index()
    grp_nft.columns = [' '.join(str(i) for i in col) for col in grp_nft.columns]
    grp_nft.reset_index(inplace=True)
    grp_nft = grp_nft.drop('index', axis=1)
    grp_nft.rename(
        columns={'NFT ': 'NFT', 'Buyer <lambda>': '# Buyers', 'Txn Hash count': '# Txns'},
        inplace=True)
    print(grp_nft.head(5))
    
    potential_fraud = grp_nft.loc[grp_nft['# Buyers'] != grp_nft['# Txns']]
    potential_fraud['Difference'] = potential_fraud.apply(lambda row: row['# Txns'] - row['# Buyers'], axis = 1)
    print(potential_fraud.head(5))
    
    filtered_potential_fraud = potential_fraud.loc[potential_fraud['Difference'] > 3]
    filtered_potential_fraud['Txns/Buyer'] = filtered_potential_fraud.apply(lambda row: row['# Txns'] / row['# Buyers'], axis = 1)
    print(filtered_potential_fraud.head(20))
    
    print_header("Converting dataframe to list")
    final_fraud_list = filtered_potential_fraud.values.tolist()
    final_fraud_list
    
    print_header("Starting the sorting")
    start = time.time()
    mergeSort(final_fraud_list, 4)
    end = time.time()
    time_taken_string = "The time of execution of above program is :" + str((end - start)) + " secs"
    print_header(time_taken_string)
    print_header("Changing the list to dataframe")
    sorted_buyer_data = pd.DataFrame(final_fraud_list,
                                     columns=['NFT',  '# Buyers',  '# Txns',  'Difference', 'Txns/Buyer'])
    print(sorted_buyer_data.info)
    print_header("Exporting the output into csv file")
    sorted_buyer_data.to_csv('sorted_fraud_nft.csv')
    print(sorted_buyer_data)
    
    # runs the dataset with various sizes & outputs the results in a csv
    # gather_runtimes(100)
    
    # pasted output csv of avg runtimes from gather_runtimes()
    # Avg Runtime,Input Size
    # 3.032684326171875e-06,4
    # 6.406307220458985e-06,8
    # 1.0359287261962891e-05,12
    # 1.4026165008544921e-05,16
    # 1.873016357421875e-05,20
    # 2.422332763671875e-05,24
    # 2.7797222137451173e-05,28
    # 3.302574157714844e-05,32
    # 3.7984848022460936e-05,36
    # 4.276275634765625e-05,40
    # 4.750967025756836e-05,44
    # 5.332708358764648e-05,48
    # 5.838871002197266e-05,52
    # 6.44397735595703e-05,56
    # 6.890296936035156e-05,60
    # 7.513046264648438e-05,64
    # 8.032560348510743e-05,68
    # 8.683919906616211e-05,72
    # 9.216070175170899e-05,76
    # 9.674072265625e-05,80
    # 0.00010288238525390625,84
    # 0.0001141810417175293,88
    # 0.0001162409782409668,92
    # 0.00012402057647705077,96
    # 0.00012792110443115234,100
    # 0.00013466358184814454,104
    # 0.00013867855072021484,108
    # 0.000144500732421875,112
    # 0.00015055179595947266,116
    # 0.00015811681747436525,120
    # 0.00016225337982177734,124
    # 0.00016922950744628905,128
    # 0.00017336606979370117,132
    # 0.00018014669418334962,136
    # 0.0001867222785949707,140
    # 0.00019291400909423828,144
    # 0.00019903659820556642,148
    # 0.00020493030548095702,152
    # 0.00021070480346679687,156
    # 0.0002199554443359375,160
    # 0.00022499561309814454,164
    # 0.00022951841354370116,168
    # 0.00023467779159545897,172
    # 0.00024103403091430665,176
    # 0.00024669170379638673,180
    # 0.00025313138961791993,184
    # 0.00025893926620483397,188
    # 0.0002660512924194336,192
    # 0.0002716064453125,196
    # 0.00027883291244506836,200
    # 0.0002840113639831543,204
    # 0.00029236555099487304,208
    # 0.00029717206954956056,212
    # 0.0003059816360473633,216
    # 0.0003108549118041992,220
    # 0.0003189420700073242,224
    # 0.0003245997428894043,228
    # 0.00033190965652465823,232
    # 0.00034493207931518555,236
    # 0.0003516912460327148,240
    # 0.0003545641899108887,244
    # 0.00036113500595092774,248
    # 0.00036803245544433596,252
    # 0.00037204265594482423,256
    # 0.00038141965866088866,260
    # 0.0003839325904846191,264
    # 0.0003937721252441406,268
    # 0.00039920568466186526,272
    # 0.00041049957275390625,276
    # 0.0004194140434265137,280
    # 0.0004237532615661621,284
    # 0.00042865753173828127,288
    # 0.00043174028396606443,292
    # 0.0004363846778869629,296
    # 0.0004457998275756836,300
    # 0.00045006752014160157,304
    # 0.0004570317268371582,308
    # 0.0004626655578613281,312
    # 0.000468904972076416,316
    # 0.0004772520065307617,320
    # 0.0004830145835876465,324
    # 0.0004900503158569336,328
    # 0.000496068000793457,332
    # 0.0005021309852600098,336
    # 0.0005121994018554688,340
    # 0.0005178236961364746,344
    # 0.000522315502166748,348
    # 0.0005484557151794433,352
    # 0.0005615139007568359,356
    # 0.000559995174407959,360

        