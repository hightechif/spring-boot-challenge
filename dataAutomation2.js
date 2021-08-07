// Valid Request
pm.test("Positive Case", function () {
    pm.response.to.be.success
    pm.response.to.be.withBody;
    pm.response.to.be.json;
})

// Add more data 2
pm.sendRequest({
    url:"localhost:8080/api/v1/employees/create",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "departmentId": 1500270123,
            "employeeId": 202100140292,
            "email": "fajar.rifai@yopmail.com",
            "name": "Fajar Rifai",
            "phoneNumber": "081220220698",
            "assignments": [
                {
                    "id": 202101003003,
                    "title": "Alert Message",
                    "description": "Develop an alert message",
                    "startDate": "2021-05-24",
                    "endDate": "2021-08-22"
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
    url:"localhost:8080/api/v1/employees/create",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "departmentId": 1500270125,
            "employeeId": 202100140293,
            "email": "nur.fathurrahman@yopmail.com",
            "name": "Nur Fathurrahman",
            "phoneNumber": "081220071998",
            "assignments": [
                {
                    "id": 202101003004,
                    "title": "Data Analytics of Indofood Sales",
                    "description": "Create Analytics Presentation about Indofood Sales",
                    "startDate": "2021-05-04",
                    "endDate": "2021-07-02"
                },
                {
                    "id": 202101003008,
                    "title": "Create a Dashboard",
                    "description": "Create a Looker Dashboard for Revenue Monitoring",
                    "startDate": "2021-04-12",
                    "endDate": "2021-06-12"
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
    url:"localhost:8080/api/v1/employees/create",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "departmentId": 1500270125,
            "employeeId": 202100140294,
            "email": "jihan.jauza@yopmail.com",
            "name": "Jihan Jauza",
            "phoneNumber": "081220071996",
            "assignments": [
                {
                    "id": 202101003005,
                    "title": "Data Analytics of Indogrosir Sales",
                    "description": "Create Analytics Presentation about Indogrosir Sales",
                    "startDate": "2021-07-24",
                    "endDate": "2021-09-22"
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
    url:"localhost:8080/api/v1/employees/create",
    method: 'POST',
    header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer '+pm.collectionVariables.get("JWT_TOKEN")
    },
    body: {
        mode: 'raw',
        raw: JSON.stringify({
            "departmentId": 1500270124,
            "employeeId": 202100140295,
            "email": "faisal.aziz@yopmail.com",
            "name": "Faisal Aziz",
            "phoneNumber": "081220220644",
            "assignments": [
                {
                    "id": 202101003006,
                    "title": "Develop data pipeline",
                    "description": "Create a data pipeline for Indofood data stream",
                    "startDate": "2021-07-04",
                    "endDate": "2021-08-02"
                },
                {
                    "id": 202101003007,
                    "title": "Create a Dashboard",
                    "description": "Create a Looker Dashboard for Revenue Monitoring",
                    "startDate": "2021-01-12",
                    "endDate": "2021-03-12"
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
