let partNumber;
let s3;
let quantity;
let token;
let barcode;

function load() {
    let params = (new URL(document.location)).searchParams;
    partNumber = params.get('partnumber');
    token = params.get('token');
    fetch(host + token + '/' + partNumber).then((items) => items.json().then((item) => {
        barcode = item.barcode;
        document.getElementById('img').src = item.photoUrl;
        quantity = item.quantity;
        document.getElementById('itemName').innerText = item.name;
        document.getElementById('itemPartNumber').innerText = 'Part #' + item.partNumber;
        document.getElementById('itemQuantity').innerText = 'QTY ' + item.quantity;
    }), (error) => {
        window.alert('Error did not complete, is the server running\n?' + error);
    })
    document.getElementById('inputquantity').focus();
}

function changeQuantity() {
    let newQuantity = prompt('Enter new quantity');

    if (newQuantity != null) {
        fetch(host + token + '/' + barcode + '/changequantity/' + newQuantity).then(() => {
            location.reload();
        }, (error) => {
            window.alert('Error, did not change quantity, is the server running\n?' + error);
        }
        );
    }
}

function changeName() {
    let newName = prompt('Enter new name');

    if (newName != null) {
        fetch(host + token + '/' + barcode + '/changename/' + newName).then(() => {
            location.reload();
        }, (error) => {
            window.alert('Error, did not change name, is the server running\n?' + error);
        }
        );
    }
}

function deleteItem() {
    fetch(host + token + '/' + barcode + '/delete').then(() => {
        location.assign('index.html?token=' + token);
    }, (error) => {
        window.alert('Error, did not delete, is the server running\n?' + error);
    }
    );

}

function addPhoto() {
    let bucketName = BUCKET_NAME;
    let bucketRegion = BUCKET_REGION;
    let IdentityPoolId = IDENTITY_POOL_ID;

    AWS.config.update({
        region: bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
            IdentityPoolId: IdentityPoolId
        })
    });

    s3 = new AWS.S3({
        apiVersion: '2006-03-01',
        params: { Bucket: bucketName }
    });

    let files = document.getElementById('fileUpload').files;
    if (files) {
        let file = files[0];
        let fileName = file.name;
        let photoKey = barcode + fileName;


        s3.upload({
            Key: photoKey,
            Body: file,
            ACL: 'public-read'
        }, function (err, data) {
            if (err) {
                alert(err);
            }
            fetch(host + token + '/' + barcode + '/changephoto/' + photoKey).then(() => {
                location.reload();
            }, () => alert('Error occured'))
        })

    }
};

function inputChanged() {
    let value = document.getElementById('inputquantity').value;
    console.log(value)

    if (value.includes('+')) {
        let amount = Number(value.substring(1));
        let newQuantity = quantity + amount;
        fetch(host + token + '/' + barcode + '/changequantity/' + newQuantity).then(() => {
            location.reload();
        })
    } else if (value.includes('-')) {
        let amount = Number(value.substring(1));
        let newQuantity = quantity - amount;
        if (newQuantity < 0) {
            newQuantity = 0;
        }
        fetch(host + token + '/' + barcode + '/changequantity/' + newQuantity).then(() => {
            location.reload();
        })
    } else if (value.includes('finish')) {
        document.getElementById('finish').click();
    }
    else {
        document.getElementById('inputquantity').value = '';
    }
}

function finish() {
    location.assign('index.html?token=' + token);
}

function changePartNumber() {
    let newPartNumber = prompt('Enter new part number');
    newPartNumber = Number(newPartNumber);
    if (newPartNumber != null) {
        fetch(host + token + '/' + barcode + '/changepartnumber/' + newPartNumber).then((response) => response.text()).then((answer) => {
            if (answer.includes('success')) {
                location.assign('editMenu.html?' + 'partnumber=' + newPartNumber+'&token='+token);
            } else {
                alert('part number already exist');
            }

        }, (error) =>
            alert('part number already exist' + error));
    }
}