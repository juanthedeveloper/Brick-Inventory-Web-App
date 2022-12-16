let validBarcodes = [];


function connect() {
    document.getElementById('input').focus();
    validBarcodes = [];

    fetch(host).then((items) => items.json()).then((item) =>
        item.forEach(item => {
            validBarcodes.push(item.barcode);

            let grid = document.getElementById('grid');

            let editButton = document.createElement('button');
            editButton.textContent = 'edit';
            editButton.onclick = function () {
                location.assign('editMenu.html?photoUrl=' + '&barcode=' + item.barcode);

            }

            let gridMenu = document.createElement('div');
            gridMenu.className = 'menu';
            gridMenu.style.textAlign = 'end';
            gridMenu.appendChild(editButton);


            let gridItem = document.createElement('div');
            gridItem.className = 'gridItem';

            let img = document.createElement('img');
            img.src = item.photoUrl;
            img.className = 'img';

            let itemInfo = document.createElement('p');
            itemInfo.innerText = 'Barcode #: ' + item.barcode + '\n' + item.quantity + 'pcs';
            itemInfo.className = 'itemInfo';

            gridItem.appendChild(gridMenu);
            gridItem.appendChild(img);
            gridItem.appendChild(itemInfo);
            grid.appendChild(gridItem);



        }))
}

function inputChanged() {
    let input = Number(document.getElementById('input').value);
    if (validBarcodes.includes(input)) {
        location.assign('editMenu.html?photoUrl=' + '&barcode=' + input);
    }
    document.getElementById('input').value = '';
}

function addItem() {
    fetch(host + 'additem').then(() => location.reload())
}