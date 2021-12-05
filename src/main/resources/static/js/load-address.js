const province = document.querySelector(".province");
const district = document.querySelector(".district");
const ward = document.querySelector(".town")

async function loadProvince() {
    let response = await fetch("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province", {
            "method": "GET",
            "headers": {
                "token": "3eac20c7-158d-11ec-b8c6-fade198b4859",
                "Content-Type": "application/json"
            }
        }
    );
    return response.json();
}

async function getDistricts(provinceId) {
    let data = {"province_id": provinceId}
    let response = await fetch("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district", {
            "method": "POST",
            "headers": {
                "token": "3eac20c7-158d-11ec-b8c6-fade198b4859",
                "Content-Type": "application/json",

            },
            "body": JSON.stringify(data)
        }
    );
    return response.json();
}

async function getWards(districtId) {
    let data = {"district_id": districtId}
    let response = await fetch(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?id=${districtId}`, {
            "method": "POST",
            "headers": {
                "token": "3eac20c7-158d-11ec-b8c6-fade198b4859",
                "Content-Type": "application/json",

            },
            "body": JSON.stringify(data)
        }
    );
    return response.json();
}

loadProvince().then(response => {
    for (p of response.data) {
        optionP = document.createElement("option")
        optionP.innerHTML = p.ProvinceName
        optionP.id = p.ProvinceID
        province.appendChild(optionP)
    }
})
changeProvince = (s) => {
    getDistricts(parseInt(s[s.selectedIndex].id)).then(response => {
        loadDistrict(response)
    })
}

function loadDistrict(response) {
    district.innerHTML = ""
    ward.innerHTML = ""
    for (d of response.data) {
        optionD = document.createElement("option")
        optionD.innerHTML = d.DistrictName
        optionD.id = d.DistrictID
        district.appendChild(optionD)
    }
}

changeDistrict = (s) => {
    getWards(parseInt(s[s.selectedIndex].id)).then(response => {
        loadWard(response)

    })
}

function loadWard(response) {
    ward.innerHTML = ""
    for (w of response.data) {
        optionW = document.createElement("option")
        optionW.innerHTML = w.WardName
        optionW.id = w.DistrictID
        ward.appendChild(optionW)
    }
}

getDistricts(269).then(response => {
    loadDistrict(response)
})
getWards(2264).then(response => {
    loadWard(response)
})
