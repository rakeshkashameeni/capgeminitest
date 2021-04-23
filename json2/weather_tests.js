let chai = require('chai')
let {expect, assert} = require('chai')
let chaiHttp = require('chai-http')

require('dotenv').config()

chai.use(chaiHttp)

let city="melbourne"
let state = "vic"
let country = "AU"
let key = process.env.KEY
let host = "api.openweathermap.org/data/2.5/weather?q="+city+","+state+
","+country+"&appid=" + key

let lat = -37.814
let lon = 144.9633

let airPollutionhost = "api.openweathermap.org/data/2.5/air_pollution\
?lat="+lat+"&lon=" + lon + "&appid=" + key

describe("Weather", ()=>{


    it("GetWeather", async ()=>{
        let res = await chai.request(host)
        .get('')
        .send()
        console.log(res);

        let json = res.body;
        expect(json.visibility).to.be.above(100)
        expect(json.coord.lon).to.be.equal(144.9633)
        expect(json.coord.lat).to.be.equal(-37.814)
        expect(json.name).to.be.equal("Melbourne")
        expect(typeof json.weather[0].main).to.be.equal("string")
        expect(json.main.temp).to.below(300)
        


    })

    it("GetAirPollution", async ()=>{
        let res = await chai.request(airPollutionhost)
        .get('')
        .send()
        console.log(res);

        let json = res.body;
        expect(json.coord.lon).to.be.equal(144.9633)
        expect(json.coord.lat).to.be.equal(-37.814)
        expect(json.list[0].components.co).to.be.above(200)

        /*
    
        expect(json.visibility).to.be.above(100)
      
        expect(json.name).to.be.equal("Melbourne")
        expect(typeof json.weather[0].main).to.be.equal("string")
        expect(json.main.temp).to.below(300)
        */


    })



})