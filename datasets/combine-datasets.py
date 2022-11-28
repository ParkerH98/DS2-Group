import os
import pandas as pd

# path = "C:\Development\DS2-Group\datasets"
path = "mom/"
dir_list = os.listdir(path)

print(dir_list)
  
file_list = [path + f for f in os.listdir(path)]

csv_list = []
 
for file in file_list:
    csv_list.append(pd.read_csv(file))

# csv_merged = pd.concat(csv_list, ignore_index=True)

# csv_merged.to_csv(path + 'nft_dataset.csv', index=False)
