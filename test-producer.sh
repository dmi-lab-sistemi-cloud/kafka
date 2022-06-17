#!/bin/bash

for i in {0..3}
do
	curl localhost:8080/send/message_${i}
done
