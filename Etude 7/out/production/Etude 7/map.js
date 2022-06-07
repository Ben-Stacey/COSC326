let Map = (function () {
    let pub = {};

    function onMapClick(e) {
        alert('You clicked the map at ' + e.latlng);
    }

    pub.setup = function () {
        map = L.map('map').setView([-45.8743, 170.5035], 15);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
            {
                maxZoom: 18,
                attribution: 'Map data &copy; ' +
                    '<a href="http://www.openstreetmap.org/copyright">' +
                    'OpenStreetMap contributors</a> CC-BY-SA'
            }).addTo(map);


        $.getJSON("GEOjson.json", function (data) {
            for (let i = 0; i < data.features.length; i++) {
                let marker = L.geoJSON(data.features[i]).addTo(map).bindPopup(data.features[i].properties.name);
            }
        });
    };

    return pub;
}());

if (window.addEventListener) {
    window.addEventListener('load', Map.setup);
} else if (window.attachEvent) {
    window.attachEvent('onload', Map.setup);
} else {
    alert("Could not attach 'Map.setup' to the 'window.onload' event");
}