package vn.namnp.stockmarketapp.data.mapper

import vn.namnp.stockmarketapp.data.local.CompanyListingEntity
import vn.namnp.stockmarketapp.data.remote.dto.CompanyInfoDto
import vn.namnp.stockmarketapp.domain.model.CompanyInfo
import vn.namnp.stockmarketapp.domain.model.CompanyListing

// data object to domain object
fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

// domain object to data object
fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}