// Valid Request
pm.test("Positive Case", function () {
    pm.response.to.be.success
    pm.response.to.be.withBody;
    pm.response.to.be.json;
})

// Add more data 2
pm.sendRequest({
    url:"localhost:8080/api/v1/students/create-new",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "firstName": "Nur",
            "lastName": "Fathurrahaman",
            "email": "nur.fathurrahman@yopmail.com",
            "dateOfBirth": "1998-04-28",
            "books": [
                    {
                        "bookName": "How to make friend and influence people"
                    },
                    {
                        "bookName": "Think and Grow Rich"
                    }
                    
                ],
            "studentIdCard" : {
                "cardNumber": "120197002"
            },
            "courses" : [
                {
                    "name" : "Python Programming",
                    "department": "Informatics"
                },
                {
                    "name" : "Statistics",
                    "department": "Mathematics"
                }
            ]
        })
    }
}, function (err, res) {
    console.info("Add more data")
    if (err) {
        console.error(err)
    } else {
        pm.test("Add more data", function () {
            pm.expect(res).to.be.withBody;
            pm.expect(res).to.be.json;
            const responseJson = res.json();
            console.log(responseJson)
        })
    }
})

// Add more data 3
pm.sendRequest({
    url:"localhost:8080/api/v1/students/create-new",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "firstName": "Najmi",
            "lastName": "Jamal",
            "email": "najmi.jamal@yopmail.com",
            "dateOfBirth": "1997-05-07",
            "books": [
                    {
                        "bookName": "Start with why"
                    }
                    
                ],
            "studentIdCard" : {
                "cardNumber": "120197003"
            },
            "courses" : [
                {
                    "name" : "French Language",
                    "department": "Literature"
                }
            ]
        })
    }
}, function (err, res) {
    console.info("Add more data")
    if (err) {
        console.error(err)
    } else {
        pm.test("Add more data", function () {
            pm.expect(res).to.be.withBody;
            pm.expect(res).to.be.json;
            const responseJson = res.json();
            console.log(responseJson)
        })
    }
})

// Add more data 4
pm.sendRequest({
    url:"localhost:8080/api/v1/students/create-new",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "firstName": "Habibul",
            "lastName": "Chair",
            "email": "habibul.chair@yopmail.com",
            "dateOfBirth": "1997-05-06",
            "books": [
                    {
                        "bookName": "Leave me or love me"
                    }
                    
                ],
            "studentIdCard" : {
                "cardNumber": "120197004"
            },
            "courses" : [
                {
                    "name" : "Stress Management",
                    "department": "Psychology"
                },
                {
                    "name" : "Human Behaviour",
                    "department": "Psychology"
                }
            ]
        })
    }
}, function (err, res) {
    console.info("Add more data")
    if (err) {
        console.error(err)
    } else {
        pm.test("Add more data", function () {
            pm.expect(res).to.be.withBody;
            pm.expect(res).to.be.json;
            const responseJson = res.json();
            console.log(responseJson)
        })
    }
})

// Add more data 5
pm.sendRequest({
    url:"localhost:8080/api/v1/students/create-new",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "firstName": "Hammam",
            "lastName": "Izzudin",
            "email": "hammam.izzudin@yopmail.com",
            "dateOfBirth": "1997-06-14",
            "books": [
                    {
                        "bookName": "Homo deus"
                    },
                    {
                        "bookName": "The Dark Knight"
                    }
                    
                ],
            "studentIdCard" : {
                "cardNumber": "120197005"
            },
            "courses" : [
                {
                    "name" : "Nuclear Safety",
                    "department": "Nuclear Engineering"
                },
                {
                    "name" : "Radioisotop",
                    "department": "Nuclear Engineering"
                }
            ]
        })
    }
}, function (err, res) {
    console.info("Add more data")
    if (err) {
        console.error(err)
    } else {
        pm.test("Add more data", function () {
            pm.expect(res).to.be.withBody;
            pm.expect(res).to.be.json;
            const responseJson = res.json();
            console.log(responseJson)
        })
    }
})

// Add more data 6
pm.sendRequest({
    url:"localhost:8080/api/v1/students/create-new",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "firstName": "Ega",
            "lastName": "Sayan",
            "email": "ega.sayan@yopmail.com",
            "dateOfBirth": "1995-2-22",
            "books": [
                    {
                        "bookName": "Atomic Habit"
                    }
                    
                ],
            "studentIdCard" : {
                "cardNumber": "120197006"
            },
            "courses" : [
                {
                    "name" : "Radioisotop",
                    "department": "Nuclear Engineering"
                }
            ]
        })
    }
}, function (err, res) {
    console.info("Add more data")
    if (err) {
        console.error(err)
    } else {
        pm.test("Add more data", function () {
            pm.expect(res).to.be.withBody;
            pm.expect(res).to.be.json;
            const responseJson = res.json();
            console.log(responseJson)
        })
    }
})

// Add more data 7
pm.sendRequest({
    url:"localhost:8080/api/v1/students/create-new",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "firstName": "Intan",
            "lastName": "Fadhilah",
            "email": "intan.fadhilah@yopmail.com",
            "dateOfBirth": "1997-12-13",
            "books": [
                    {
                        "bookName": "Kembali ke surga"
                    }
                    
                ],
            "studentIdCard" : {
                "cardNumber": "120197007"
            },
            "courses" : [
                {
                    "name" : "French Poets",
                    "department": "Literature"
                }
            ]
        })
    }
}, function (err, res) {
    console.info("Add more data")
    if (err) {
        console.error(err)
    } else {
        pm.test("Add more data", function () {
            pm.expect(res).to.be.withBody;
            pm.expect(res).to.be.json;
            const responseJson = res.json();
            console.log(responseJson)
        })
    }
})

// Add more data 8
pm.sendRequest({
    url:"localhost:8080/api/v1/students/create-new",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "firstName": "Ridhan",
            "lastName": "Jaeger",
            "email": "ridhan.jaeger@yopmail.com",
            "dateOfBirth": "1991-3-27",
            "books": [
                    {
                        "bookName": "Menangkap ubur-ubur"
                    },
                    {
                        "bookName": "Memasak krabby patty"
                    }
                    
                ],
            "studentIdCard" : {
                "cardNumber": "120197008"
            },
            "courses" : [
                {
                    "name" : "Fast Food",
                    "department": "Culinary"
                }
            ]
        })
    }
}, function (err, res) {
    console.info("Add more data")
    if (err) {
        console.error(err)
    } else {
        pm.test("Add more data", function () {
            pm.expect(res).to.be.withBody;
            pm.expect(res).to.be.json;
            const responseJson = res.json();
            console.log(responseJson)
        })
    }
})

// Add more data 9
pm.sendRequest({
    url:"localhost:8080/api/v1/students/create-new",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "firstName": "Patrick",
            "lastName": "Star",
            "email": "patrick.star@yopmail.com",
            "dateOfBirth": "1999-02-23",
            "books": [
                {
                    "bookName" : "Menjadi seorang Fadhilah"
                }
            ],
            "studentIdCard" : {
                "cardNumber": "120197009"
            },
            "courses" : [
                {
                    "name" : "Singing",
                    "department": "Musics"
                }
            ]
        })
    }
}, function (err, res) {
    console.info("Add more data")
    if (err) {
        console.error(err)
    } else {
        pm.test("Add more data", function () {
            pm.expect(res).to.be.withBody;
            pm.expect(res).to.be.json;
            const responseJson = res.json();
            console.log(responseJson)
        })
    }
})

// Add more data 10
pm.sendRequest({
    url:"localhost:8080/api/v1/students/create-new",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "firstName": "Eren",
            "lastName": "Jaeger",
            "email": "eren.jaeger@yopmail.com",
            "dateOfBirth": "1997-01-12",
            "books": [
                    {
                        "bookName": "Eldia History"
                    },
                    {
                        "bookName": "Manuver Gear"
                    },
                    {
                        "bookName": "Ridhan Fadhilah"
                    }
                ],
            "studentIdCard" : {
                "cardNumber": "120197010"
            },
            "courses" : [
                {
                    "name" : "Singing",
                    "department": "Musics"
                },
                {
                    "name" : "Titan Anatomy",
                    "department": "Biology"
                }
            ]
        })
    }
}, function (err, res) {
    console.info("Add more data")
    if (err) {
        console.error(err)
    } else {
        pm.test("Add more data", function () {
            pm.expect(res).to.be.withBody;
            pm.expect(res).to.be.json;
            const responseJson = res.json();
            console.log(responseJson)
        })
    }
})
