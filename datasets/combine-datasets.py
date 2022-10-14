import os
import pandas as pd

path = "C:\Development\DS2-Group\datasets\\"
dir_list = os.listdir(path)
  
file_list = [path + f for f in os.listdir(path)]

csv_list = []
 
for file in file_list:
    csv_list.append(pd.read_csv(file).assign(File_Name = os.path.basename(file)))

csv_merged = pd.concat(csv_list, ignore_index=True)

csv_merged.to_csv(path + 'nft_dataset.csv', index=False)
