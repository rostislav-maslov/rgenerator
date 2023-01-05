db = db.getSiblingDB('rgenerator');
db.createCollection("firstcollection");
db.createUser({
    user: "rgeneratoruser",
    pwd: "rg3n3r@toRuSer",
    roles: [
        {
            "role" : "dbAdmin",
            "db" : "rgenerator"
        },
        {
            "role" : "root",
            "db" : "admin"
        },
        {
            "role" : "dbOwner",
            "db" : "rgenerator"
        },
        {
            "role" : "enableSharding",
            "db" : "rgenerator"
        },
        {
            "role" : "read",
            "db" : "rgenerator"
        },
        {
            "role" : "readWrite",
            "db" : "rgenerator"
        },
        {
            "role" : "userAdmin",
            "db" : "rgenerator"
        }
    ]
});
