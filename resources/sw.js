self.addEventListener('fetch', function (e) {
  console.log('[Service Worker] Fetched resource '+e.request.url)
})
