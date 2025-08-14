const CACHE_NAME = 'easyturno-cache-v1';
const APP_SHELL_URLS = [
  '/',
  '/index.html',
  // Since Vite produces hashed assets, I can't know the exact names.
  // Caching '/' and '/index.html' ensures the entry point is cached.
  // The browser will then request the JS/CSS files, which will be cached at runtime
  // by the fetch event handler. I'll also add common paths.
  '/src/index.css',
  '/src/main.tsx',
  '/vite.svg' // The default favicon
];

// On install, pre-cache the app shell
self.addEventListener('install', (event) => {
  console.log('Service Worker: Installing...');
  event.waitUntil((async () => {
    const cache = await caches.open(CACHE_NAME);
    console.log('Service Worker: Caching app shell');
    // Use addAll for atomic operation
    try {
      await cache.addAll(APP_SHELL_URLS);
    } catch (error) {
      console.error('Service Worker: Failed to cache app shell:', error);
      // If any file fails, the whole cache operation fails.
      // This is often desired for the app shell.
    }
  })());
});

// On activate, clean up old caches
self.addEventListener('activate', (event) => {
  console.log('Service Worker: Activating...');
  event.waitUntil((async () => {
    const cacheNames = await caches.keys();
    await Promise.all(
      cacheNames.map((cacheName) => {
        if (cacheName !== CACHE_NAME) {
          console.log('Service Worker: Deleting old cache', cacheName);
          return caches.delete(cacheName);
        }
      })
    );
  })());
});

// On fetch, use stale-while-revalidate strategy
self.addEventListener('fetch', (event) => {
    // We only want to cache GET requests
    if (event.request.method !== 'GET') {
        return;
    }

    event.respondWith((async () => {
        const cache = await caches.open(CACHE_NAME);
        const cachedResponse = await cache.match(event.request);

        const fetchPromise = fetch(event.request).then((networkResponse) => {
            // If the request is successful, update the cache
            if (networkResponse.ok) {
              cache.put(event.request, networkResponse.clone());
            }
            return networkResponse;
        }).catch(err => {
            console.error("Service Worker: fetch failed.", err);
            // If fetch fails, and we have nothing in cache, we will fail.
            // This is expected.
        });

        // Return cached response immediately if available,
        // otherwise wait for the network response.
        return cachedResponse || fetchPromise;
    })());
});
