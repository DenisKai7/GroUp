# Scraping Api Spec

## GET Scraping Api

Endpoint: GET /api/scraping

Response Body Success:

```json
{
  "data": [
    {
      "title": "Curhat Bidan Karawang Lawan Stunting, Harus Hadapi Penolakan Ortu-Mitos Masyarakat",
      "urlImage": "https://akcdn.detik.net.id/community/media/visual/2024/12/05/pelayanan-puskesmas-di-puskemas-ciampel-kabupaten-karawang-jawa-barat_43.jpeg?w=300&q=80",
      "urlWeb": "https://health.detik.com/berita-detikhealth/d-7672989/curhat-bidan-karawang-lawan-stunting-harus-hadapi-penolakan-ortu-mitos-masyarakat",
      "scrapedAt": "2024-12-06T04:22:51.812Z"
    },
    {
      "title": "Menteri Wihaji Luncurkan Program Genting demi Turunkan Stunting",
      "urlImage": "https://akcdn.detik.net.id/community/media/visual/2024/12/05/menteri-kependudukan-dan-pembangunan-keluarga-wihaji-saat-peluncuran-program-genting-di-kecamatan-ciampel-kabupaten-karawang_43.jpeg?w=300&q=80",
      "urlWeb": "https://news.detik.com/berita/d-7672492/menteri-wihaji-luncurkan-program-genting-demi-turunkan-stunting",
      "scrapedAt": "2024-12-06T04:22:51.814Z"
    }
  ]
}
```

Response Body Erros:

```json
{
  "errors": "Error scraping website url"
}
```
