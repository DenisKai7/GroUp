# Scraping Api Spec

## GET Scraping Api

Endpoint: Get /api/scraping

Response Body Success:

```json
{
  "data": [
    {
      "title": "Inovasi Produk Rendang Khusus Anak Ini Bisa Jadi Solusi Stunting",
      "urlImage": "https://akcdn.detik.net.id/community/media/visual/2024/12/01/rendang-untuk-anak_43.jpeg?w=300&q=80",
      "scrapedAt": "2024-12-06T03:40:25.078Z"
    },
    {
      "title": "Menteri Pembangunan Keluarga Bicara Susahnya Turunkan Stunting RI, Ini Penyebabnya",
      "urlImage": "https://akcdn.detik.net.id/community/media/visual/2024/12/05/menteri-kependudukan-dan-pembangunan-keluarga-mendukbangga-wihaji_43.jpeg?w=300&q=80",
      "scrapedAt": "2024-12-06T03:40:25.077Z"
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
