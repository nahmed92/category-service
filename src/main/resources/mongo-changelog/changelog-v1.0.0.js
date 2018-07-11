// mongeez formatted javascript
// %%Ignore-License

// changeset ehashmi:CAS-7
db.categories.dropIndex({
    "name" : 1,
    "industryId" : 1
});
db.categories.createIndex({
    "name" : 1,
    "industryId" : 1
}, {
    "unique" : true,
    "collation" : {
        "locale" : "en",
        "strength" : 2
    }
});

// changeset uzubair:CAS-46
db.categories.updateMany({"identifiers": {$exists: false}}, { $set : {"identifiers":[]}});

// changeset nasahmed:CAS-49
db.categories.updateMany({"attributes": { $exists: true } }, { $rename: {"attributes": "specificationAttributes" }});
db.categories.updateMany({"mediaAttributes": {$exists: false}}, { $set : {"mediaAttributes":[]}});
