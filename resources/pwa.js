const frame = document.createElement('iframe')
frame.style = 'border: 0; width: 100vw; height: calc(100vh + 1px)'
frame.src = location.search.slice(1)
document.body.appendChild(frame)

if ('serviceWorker' in navigator) {
  navigator.serviceWorker.register('/sw.js')
}

function setThemeColor(color) {
  const metaThemeColor = document.querySelector("meta[name=theme-color]")
  metaThemeColor.setAttribute("content", color)
}

window.addEventListener('message', ({ data }) => {
  const event = JSON.parse(data)
  console.log(event)
  switch (event.type) {
    case 'close':
      window.close()
      break
    case 'set-theme':
      setThemeColor(event.color)
      break;
  }
})
