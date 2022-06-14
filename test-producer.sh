#!/bin/bash

for i in {0..4}
do
	curl localhost:8080/send/message_${i}
done
