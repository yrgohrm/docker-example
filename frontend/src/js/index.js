import config from './configuration.js'

const hsform = document.getElementById("hsform")
hsform.addEventListener("submit", async (evt) => {
    evt.preventDefault()

    const player = encodeURIComponent(document.getElementById("player").value)
    const score = encodeURIComponent(document.getElementById("score").value)

    const url = `${config.server}/highscore?player=${player}&score=${score}`

    const resp = await fetch(url, {
        method: "POST"
    })

    if (resp.ok) {
        fetchHighscore()
    }
})

async function fetchHighscore() {
    const resp = await fetch(`${config.server}/highscore`, {
        headers: { 'Accept': 'application/json' }
    })

    if (!resp.ok) {
        return;
    }

    const json = await resp.json()

    const highscore = document.getElementById("highscore")
    highscore.innerHTML = ''

    for (const hs of json.highscores) {
        const div = document.createElement('div')
        div.classList.add('row')
        div.innerHTML = `<div class="col">${hs.player}</div>
                         <div class="col">${hs.score}</div>`
        highscore.append(div)
    }
}

fetchHighscore()
