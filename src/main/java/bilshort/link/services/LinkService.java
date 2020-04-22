package bilshort.link.services;

import bilshort.link.models.Link;
import bilshort.link.models.LinkDTO;

import java.util.List;

public interface LinkService {
    Link createShortLink(LinkDTO linkDTO);
    List<Link> getAllLinks();
    Link getLinkById(Integer id);
    Long deleteLinkById(Integer id);
    Link updateLink(Link link);
    List<Link> getLinksByUserId(Integer userId);
    Link getLinkByCode(String code);
    List<Link> getLinksByUserName(String userName);
}
