let barcode;
let s3;
let quantity;

function load() {
    let params = (new URL(document.location)).searchParams;
    barcode = params.get('barcode');
    fetch(host + barcode).then((items) => items.json().then((item) => {
        document.getElementById('img').src = item.photoUrl;
        quantity=item.quantity;
        document.getElementById('itemInfo').innerText = 'Barcode #: ' + barcode + '\n' + item.quantity + 'pcs';
    }), (error) => {
        window.alert('Error did not complete, is the server running\n?' + error);
    })
    document.getElementById('inputquantity').focus();
}

function changeQuantity() {
    let newQuantity = prompt('Enter new quantity');

    if (newQuantity != null) {
        fetch(host + 'changequantity/' + barcode + '/' + newQuantity).then(() => {
            location.reload();
        }, (error) => {
            window.alert('Error, did not change quantity, is the server running\n?' + error);
        }
        );
    }
}

function deleteItem() {
    fetch(host + barcode + '/delete').then(() => {
        location.assign('index.html');
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
                reject('error');
            }
            fetch(host + barcode + '/changephoto/' + photoKey).then(() => {
                location.reload();
            }, () => alert('Error occured'))
        })

    }
};

function inputChanged(){
    let value = document.getElementById('inputquantity').value;
    console.log(value)

    if (value.includes('+')){
        let amount = Number(value.substring(1));
        let newQuantity= quantity+amount;
        fetch(host + 'changequantity/' + barcode + '/' + newQuantity).then(() => {
            location.reload();
        })
    }else if(value.includes('-')){
        let amount = Number(value.substring(1));
        let newQuantity= quantity-amount;
        if (newQuantity<0){
            newQuantity=0;
        }
        fetch(host + 'changequantity/' + barcode + '/' + newQuantity).then(() => {
            location.reload();
        })
    }else if(value.includes('finish')){
        document.getElementById('finish').click();
    }
    else{
        document.getElementById('inputquantity').value='';
    }
    
    
    
}