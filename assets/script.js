var mapData, pos;
var trn = ["반월당", "청라언덕", "명덕"];
var req = new XMLHttpRequest();
req.open("GET", "./stn_data.json");
req.onreadystatechange = function(e) {
    if (this.readyState == 4 && req.status === 200) {
        data = (req.responseText + "").trim();
        createMap(JSON.parse(data));
    }
}
req.send();

function createMap(data) {
    pos = data;
    var map = document.getElementById("map");
    var src = '';
    for (var n = 0; n < 3; n++) {
        pos[n].forEach((e) => {
            src += '<circle cx="' + e.x + '" cy="' + e.y + '" r="11" onClick="onIconClicked(\'' + e.stn + '\');" />';
            if (trn.includes(e.stn)) return;
            var x = e.x,
                y = e.y;
            var dir = e.dir;
            if (dir == null) dir = 'left';
            if (dir == 'left') x += 30;
            if (dir == 'right') x -= 30;
            if (dir == 'center') y += 50;
            src += '<text x=' + x + ' y=' + y + ' onClick="onTextClicked(this);" class=' + dir + '>' + e.stn + '</text>';
        });
    }
    var m = [16, 13, 19];
    for (var n = 0; n < trn.length; n++) {
        src += '<text x=' + (pos[n][m[n]].x + 20) + ' y=' + (pos[n][m[n]].y + 40) + ' onClick="onTextClicked(this);" class="left trn">' + pos[n][m[n]].stn + '</text>';
    }
    map.innerHTML += src;
    mapData = map.innerHTML;
}

function updateData(data) {
    data = JSON.parse(data);
    data[0].forEach((e, i) => {
        var tmp = e.up;
        data[0][i].up = e.dn;
        data[0][i].dn = tmp;
    });
    data[0].reverse();

    var map = document.getElementById("map");
    var src = '';
    data.forEach((e, i) => {
        e.forEach((e, j) => {
            var p = pos[i][j], size = 20;
            var x = p.x, y = p.y;
            y -= 20;
            if (p.rot) {
                size = 40;
            }

            if (e.up) {
                x += 10;
                var dir = 'up';
                if (p.rot) {
                    dir = p.rot[dir];
                    x -= 30;
                    if (dir == 'left') y -= 10;
                    if (dir == 'right') y += 30;
                }
                src += '<image xlink:href="images/' + dir + '.png" x="' + x + '" y="' + y + '" width="' + size + 'px" opacity="0.7" />';
            }

            x = pos[i][j].x;
            y = p.y - 20;
            if (e.dn) {
                x -= 30;
                var dir = 'down';
                if (p.rot) {
                    dir = p.rot[dir];
                    x += 10;
                    if (dir == 'left') y -= 10;
                    if (dir == 'right') y += 30;
                }
                src += '<image xlink:href="images/' + dir + '.png" x="' + x + '" y="' + y + '" width="' + size + 'px" opacity="0.7" />';
            }
        });
    });
    map.innerHTML = mapData + src;
}

