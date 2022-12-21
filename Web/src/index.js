let validPartNumbers = [];



function connect() {
    let params = (new URL(document.location)).searchParams;
    let token = params.get('token');
    document.getElementById('input').focus();
    validPartNumbers = [];

    fetch(host+token).then((items) => items.json()).then((item) =>
        item.forEach(item => {
            validPartNumbers.push(item.partNumber);

            let grid = document.getElementById('grid');

            let editButton = document.createElement('button');
            editButton.textContent = 'edit';
            editButton.onclick = function () {
                location.assign('editMenu.html?' + 'partnumber=' + item.partNumber+'&token='+token);

            }

            let gridMenu = document.createElement('div');
            gridMenu.className = 'menu';
            gridMenu.style.textAlign = 'end';
            gridMenu.appendChild(editButton);


            let gridItem = document.createElement('div');
            gridItem.className = 'gridItem';

            let img = document.createElement('img');
            img.src = item.photoUrl;
            img.alt ='default.jpg';
            img.className = 'img';

            let itemName = document.createElement('p');
            itemName.innerText= item.name;
            itemName.className = 'itemName';
            

            let partNumber = document.createElement('p');
            partNumber.innerText = 'Part #' + item.partNumber;
            partNumber.className = 'partNumber';

            let itemQuantity = document.createElement('p');
            itemQuantity.innerText = 'QTY ' + item.quantity;
            itemQuantity.className = 'itemQuantity';

            gridItem.appendChild(gridMenu);
            gridItem.appendChild(img);
            gridItem.appendChild(itemName);
            gridItem.appendChild(partNumber);
            gridItem.appendChild(itemQuantity);
            grid.appendChild(gridItem);



        }))
}

function inputChanged() {
    let params = (new URL(document.location)).searchParams;
    let token = params.get('token');
    let input = Number(document.getElementById('input').value);
    if (validPartNumbers.includes(input)) {
        location.assign('editMenu.html?' + 'partnumber=' + input+'&token='+ token);
    }
    document.getElementById('input').value = '';
}

function addItem() {
    let params = (new URL(document.location)).searchParams;
   let token = params.get('token');
    fetch(host+token + '/additem').then(() => {
        location.reload();
    })
}