# Multi-threaded HTTP Web Server

A lightweight, fast, and multithreaded HTTP server built in Java. Designed to handle concurrent connections efficiently using a thread pool.

## Quick Start
```bash
# Compile the project
./build.sh

# Run the server
./run.sh
```
Then navigate to `http://localhost:8080/` in your browser.

## Performance Testing
To test the server's concurrency and throughput using ApacheBench:
```bash
ab -n 1000 -c 50 http://localhost:8080/
```
