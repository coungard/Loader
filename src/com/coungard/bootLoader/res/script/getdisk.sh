#!/bin/bash


#mounted=( $(cat /proc/mounts | grep /dev/sd | gawk '{print substr($1, 1, 8)}' | sort -u) )

echo "sda 45G"
echo "sdc 245G"
echo "sdb 5G"
echo "sduu 3345G"
