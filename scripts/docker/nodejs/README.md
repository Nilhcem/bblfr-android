# Create

```bash
docker build -t nilhcem/nodejs .
```

# Start

```bash
docker run --volume=`pwd`/../../../mockserver:/srv -p 8990:8990 -d nilhcem/nodejs
```
