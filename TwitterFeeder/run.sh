#!/bin/bash

# This is a helper script to run the application
# After modifying it to your project, you can use it as following:
#
# ./run.sh LinkListener
# ./run.sh SearchResultsServer
# ./run.sh TwitterListener

# Configure AWS environment variable for SDK API
export AWS_ACCESS_KEY_ID=AKIAJTFZ4PRBLK4QGNSA
export AWS_SECRET_ACCESS_KEY=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
export AWS_REGION=us-east-1

# Run the java program with different main as user provided
# Also send the correct system properties (don't forget backslash before newline)
nohup java -cp TwitterFeeder-1.0-SNAPSHOT-jar-with-dependencies.jar \
    -DTWITTER_API_KEY=kXQ7gvh6ZPzabRO9SWP8nzfcz \
    -DTWITTER_API_SECRET_KEY=UnO9NNROkTGb5WWMkfxeIKPz3eazqxZXsjSsor2xhziLrDnNK9 \
    -DTWITTER_ACCESS_TOKEN=1036263641132294144-7v85hiDTWTQ93R1qj2b2AoTSS10r13 \
    -DTWITTER_ACCESS_TOKEN_SECRET=5q3VYKMZbMH8EPy4Ao5eaiZHoSbHUZBvMJysElNGalwXm \
    -Dmax_links=1000 \
    -Dtweets_stream_delay=5000 \
    -Dtweets_read_delay=3000 \
    -Dtrack=apple \
    -Dqueue_url="https://sqs.us-east-1.amazonaws.com/135062767808/yonof" \
    -Ddb_connection="jdbc:mysql://yonof.cwn8zwzjkufz.us-east-1.rds.amazonaws.com:3306/yonof?user=yonof&password=Aa123456" \
    il.ac.colman.cs.$1 &
