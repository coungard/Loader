#!/bin/bash

if [  -r $1 ] ;
then

echo "disk not select"  >/tmp/install.log
exit 1
fi

echo "install into $1" >/tmp/install.log
echo "format"  >>/tmp/install.log
sleep 5
echo "copy" >>/tmp/install.log
sleep 5
echo "img_packaging" >>/tmp/install.log
sleep 5
echo "bootloader" >>/tmp/install.log
sleep 5
echo "finish" >>/tmp/install.log







