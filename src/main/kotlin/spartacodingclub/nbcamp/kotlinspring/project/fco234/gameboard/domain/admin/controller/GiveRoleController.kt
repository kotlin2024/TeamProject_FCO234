package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.controller

import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.admin.dto.GiveRoleRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.auth.admin.service.GiveRoleService
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.request.GiveRoleRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.service.GiveRoleService

@RestController
class GiveRoleController (

    private val giveRoleService: GiveRoleService
) {

    @PutMapping("/admin")
    fun giveRole(
        @RequestBody giveRoleRequest: GiveRoleRequest
    ) {
        giveRoleService.giveRole(giveRoleRequest.userId,giveRoleRequest.giveRole)
    }

}